package com.heub.selectcourse.excel;

import cn.hutool.core.date.DateTime;
import com.heub.selectcourse.service.LearningLessonService;
import com.heub.selectcourse.service.StudentService;
import com.heub.selectcourse.service.TeachingClassService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/data")
@Slf4j
public class Excel {
	
	@Resource
	private LearningLessonService learningLessonService;
	@Resource
	private StudentService studentService;
	@Resource
	private TeachingClassService teachingClassService;
	
	
	@GetMapping()
	public ResponseEntity<InputStreamResource> generateExcel() throws Exception {
		List<Long> classIds = teachingClassService.getAllId();
		List<File> excelFiles = new ArrayList<>();
		
		// 为每个 classid 生成一个 Excel 文件
		for (Long classId : classIds) {
			String fileName = teachingClassService.getTeachingClassName(Math.toIntExact(classId));
			List<String> studentNumbers = learningLessonService.searchStudentNumberByLessonId(classId);
			List<studentInfo> studentInfoList = studentService.getStudentInfoList(studentNumbers);
			
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet();
			Row row1 = sheet.createRow(0);
			
			Cell cell1 = row1.createCell(0);
			cell1.setCellValue("姓名");
			Cell cell2 = row1.createCell(1);
			cell2.setCellValue("学号");
			Cell cell3 = row1.createCell(2);
			cell3.setCellValue("年级");
			Cell cell4 = row1.createCell(3);
			cell4.setCellValue("学院");
			Cell cell5 = row1.createCell(4);
			cell5.setCellValue("专业");
			
			int rowIndex = 1;
			for (studentInfo student : studentInfoList) {
				Row dataRow = sheet.createRow(rowIndex);
				dataRow.createCell(0).setCellValue(student.getStudentName());
				dataRow.createCell(1).setCellValue(student.getStudentNumber());
				dataRow.createCell(2).setCellValue(student.getGrade());
				dataRow.createCell(3).setCellValue(student.getCollege());
				dataRow.createCell(4).setCellValue(student.getMajor());
				rowIndex++;
			}
			
			// 将 Excel 文件写入临时文件
			String tempFileName = fileName + "-" + classId + "_" + DateTime.now().toString("yyyyMMddHHmmss");
			File tempFile = File.createTempFile(tempFileName, ".xlsx");
			try (FileOutputStream fos = new FileOutputStream(tempFile)) {
				workbook.write(fos);
			}
			workbook.close();
			excelFiles.add(tempFile);
		}
		
		// 创建 ZIP 文件
		File zipFile = File.createTempFile("course-selection-data", ".zip");
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
			for (File excelFile : excelFiles) {
				try (FileInputStream fis = new FileInputStream(excelFile)) {
					ZipEntry zipEntry = new ZipEntry(excelFile.getName());
					zos.putNextEntry(zipEntry);
					byte[] buffer = new byte[1024];
					int len;
					while ((len = fis.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
					zos.closeEntry();
				}
			}
		}
		
		// 设置 ZIP 文件下载响应
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String encodedZipFileName = URLEncoder.encode("course-selection-data.zip", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
		headers.add("Content-Disposition", "attachment; filename*=UTF-8''" + encodedZipFileName);
		
		// 返回 ZIP 文件
		InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
		return ResponseEntity.ok().headers(headers).body(resource);
	}
	
}