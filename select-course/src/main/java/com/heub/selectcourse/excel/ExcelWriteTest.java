package com.heub.selectcourse.excel;

import cn.hutool.core.date.DateTime;
import com.heub.selectcourse.excel.studentInfo;
import com.heub.selectcourse.model.domain.LearningLesson;
import com.heub.selectcourse.model.domain.TeachingClass;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelWriteTest {

    @Resource
    private LearningLessonService learningLessonService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeachingClassService teachingClassService;
    static String PATH = "D:\\IDEA-Pro\\course-selection-system-server\\select-course";

    @GetMapping("/out")
    public ResponseEntity<FileSystemResource> generateExcel(Long classid) throws Exception {
        String fileName = teachingClassService.getTeachingClassName(Math.toIntExact(classid));
        List<String> studentNumbers = learningLessonService.searchStudentNumberByLessonId(classid);
        List<studentInfo> studentInfoList = studentService.getStudentInfoList(studentNumbers);

        // 1.创建一个工作簿
        Workbook workbook = new XSSFWorkbook();
        // 2.创建一个工作表
        Sheet sheet = workbook.createSheet();
        // 3.创建一个行(1,1)
        Row row1 = sheet.createRow(0);

        // 4.创建标题单元格并设置值
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
            Cell nameCell = dataRow.createCell(0);
            nameCell.setCellValue(student.getStudentName());
            Cell numberCell = dataRow.createCell(1);
            numberCell.setCellValue(student.getStudentNumber());
            Cell gradeCell = dataRow.createCell(2);
            gradeCell.setCellValue(student.getGrade());
            Cell collegeCell = dataRow.createCell(3);
            collegeCell.setCellValue(student.getCollege());
            Cell majorCell = dataRow.createCell(4);
            majorCell.setCellValue(student.getMajor());
            rowIndex++;
        }

        // 生成文件并保存
        String outputFileName = fileName + classid + "_" + DateTime.now().toString("yyyyMMddHHmmss") + ".xlsx";
        File outputFile = new File(PATH + File.separator + outputFileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            workbook.write(fileOutputStream);
        }

        // 返回文件下载响应
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", outputFileName);
        return ResponseEntity.ok().headers(headers).body(new FileSystemResource(outputFile));
    }

}