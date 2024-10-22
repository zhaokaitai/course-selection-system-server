package com.heub.selectcourse.excel;

import cn.hutool.core.date.DateTime;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.heub.selectcourse.common.BaseResponse;
import com.heub.selectcourse.common.ErrorCode;
import com.heub.selectcourse.common.ResultUtils;
import com.heub.selectcourse.model.domain.*;
import com.heub.selectcourse.service.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
	@Resource
	private CourseService courseService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private CollegeService collegeService;
	@Resource
	private MajorService majorService;
	
	
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
	
	@PostMapping("upload")
	public BaseResponse<Integer> uploadFile(@RequestParam("type") String type, @RequestParam("file") MultipartFile file) {
		return switch (type) {
			case "student" -> ResultUtils.success(uploadStudent(file));
			case "course" -> ResultUtils.success(uploadCourse(file));
			case "class" -> ResultUtils.success(uploadClass(file));
			case "teacher" -> ResultUtils.success(uploadTeacher(file));
			case "college" -> ResultUtils.success(uploadCollege(file));
			case "major" -> ResultUtils.success(uploadMajor(file));
			default -> ResultUtils.error(ErrorCode.PARAMS_ERROR);
		};
	}
	
	@PostMapping()
	public ResponseEntity<InputStreamResource> generateClassExcel() throws Exception {
		List<TeachingClass> teachingClassList = teachingClassService.lambdaQuery().lt(TeachingClass::getSelectedNum, 20).list();
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Row row1 = sheet.createRow(0);
		
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue("课程代码");
		Cell cell2 = row1.createCell(1);
		cell2.setCellValue("教学班名称");
		Cell cell3 = row1.createCell(2);
		cell3.setCellValue("上课教室");
		Cell cell4 = row1.createCell(3);
		cell4.setCellValue("上课时间");
		Cell cell5 = row1.createCell(4);
		cell5.setCellValue("上课地点");
		Cell cell6 = row1.createCell(5);
		cell6.setCellValue("教师姓名");
		
		int rowIndex = 1;
		for (TeachingClass teachingClass : teachingClassList) {
			Row dataRow = sheet.createRow(rowIndex);
			dataRow.createCell(0).setCellValue(teachingClass.getCourseCode());
			dataRow.createCell(1).setCellValue(teachingClass.getClassName());
			dataRow.createCell(2).setCellValue(teachingClass.getClassroom());
			dataRow.createCell(3).setCellValue(teachingClass.getClassTime());
			dataRow.createCell(4).setCellValue(teachingClass.getClassPlace());
			dataRow.createCell(5).setCellValue(teacherService.getById(teachingClass.getTeacherId()).getTeacherName());
			rowIndex++;
		}
		
		// 将 Excel 文件写入临时文件
		String tempFileName = "未能开课名单" + "_" + DateTime.now().toString("yyyyMMddHHmmss");
		File tempFile = File.createTempFile(tempFileName, ".xlsx");
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			workbook.write(fos);
		}
		workbook.close();
		
		// 设置文件下载响应
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String encodedFileName = URLEncoder.encode(tempFileName + ".xlsx", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
		headers.add("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
		
		// 返回文件
		InputStreamResource resource = new InputStreamResource(new FileInputStream(tempFile));
		return ResponseEntity.ok().headers(headers).body(resource);
	}
	
	// 导入学生
	private int uploadStudent(MultipartFile file) {
		List<Student> studentList = new ArrayList<>();
		try {
			// 使用 EasyExcel 读取 Excel 文件
			EasyExcel.read(file.getInputStream(), Student.class, new AnalysisEventListener<Student>() {
				@Override
				public void invoke(Student student, AnalysisContext analysisContext) {
					// 处理每行数据
					studentList.add(student);
				}
				
				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {
					// 所有数据解析完成后的操作
				}
			}).sheet().doRead();
			
			// 删除数据库内容
			studentService.deleteAll();
			
			// 向数据库插入解析后的数据
			boolean result = studentService.saveBatch(studentList);
			return result ? 1 : 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
			// return 0;
		}
	}
	
	// 导入课程
	private int uploadCourse(MultipartFile file) {
		List<Course> courseList = new ArrayList<>();
		try {
			// 使用 EasyExcel 读取 Excel 文件
			EasyExcel.read(file.getInputStream(), Course.class, new AnalysisEventListener<Course>() {
				@Override
				public void invoke(Course course, AnalysisContext analysisContext) {
					// 处理每行数据
					courseList.add(course);
				}
				
				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {
					// 所有数据解析完成后的操作
				}
			}).sheet().doRead();
			
			// 删除数据库内容
			courseService.deleteAll();
			
			// 向数据库插入解析后的数据
			boolean result = courseService.saveBatch(courseList);
			return result ? 1 : 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	// 导入教学班
	private int uploadClass(MultipartFile file) {
		List<TeachingClass> teachingClassList = new ArrayList<>();
		try {
			// 使用 EasyExcel 读取 Excel 文件
			EasyExcel.read(file.getInputStream(), TeachingClass.class, new AnalysisEventListener<TeachingClass>() {
				@Override
				public void invoke(TeachingClass teachingClass, AnalysisContext analysisContext) {
					// 处理每行数据
					teachingClassList.add(teachingClass);
				}
				
				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {
					// 所有数据解析完成后的操作
				}
			}).sheet().doRead();
			
			// 删除数据库内容
			teachingClassService.deleteAll();
			
			// 向数据库插入解析后的数据
			boolean result = teachingClassService.saveBatch(teachingClassList);
			return result ? 1 : 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	// 导入教师
	private int uploadTeacher(MultipartFile file) {
		List<Teacher> teacherList = new ArrayList<>();
		try {
			// 使用 EasyExcel 读取 Excel 文件
			EasyExcel.read(file.getInputStream(), Teacher.class, new AnalysisEventListener<Teacher>() {
				@Override
				public void invoke(Teacher teacher, AnalysisContext analysisContext) {
					// 处理每行数据
					teacherList.add(teacher);
				}
				
				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {
					// 所有数据解析完成后的操作
				}
			}).sheet().doRead();
			
			// 删除数据库内容
			teacherService.deleteAll();
			
			// 向数据库插入解析后的数据
			boolean result = teacherService.saveBatch(teacherList);
			return result ? 1 : 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	// 导入学院
	private int uploadCollege(MultipartFile file) {
		List<College> collegeList = new ArrayList<>();
		try {
			// 使用 EasyExcel 读取 Excel 文件
			EasyExcel.read(file.getInputStream(), College.class, new AnalysisEventListener<College>() {
				@Override
				public void invoke(College college, AnalysisContext analysisContext) {
					// 处理每行数据
					collegeList.add(college);
				}
				
				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {
					// 所有数据解析完成后的操作
				}
			}).sheet().doRead();
			
			// 删除数据库内容
			collegeService.deleteAll();
			
			// 向数据库插入解析后的数据
			boolean result = collegeService.saveBatch(collegeList);
			return result ? 1 : 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	// 导入专业
	private int uploadMajor(MultipartFile file) {
		List<Major> majorList = new ArrayList<>();
		try {
			// 使用 EasyExcel 读取 Excel 文件
			EasyExcel.read(file.getInputStream(), Major.class, new AnalysisEventListener<Major>() {
				@Override
				public void invoke(Major major, AnalysisContext analysisContext) {
					// 处理每行数据
					majorList.add(major);
				}
				
				@Override
				public void doAfterAllAnalysed(AnalysisContext analysisContext) {
					// 所有数据解析完成后的操作
				}
			}).sheet().doRead();
			
			// 删除数据库内容
			majorService.deleteAll();
			
			// 向数据库插入解析后的数据
			boolean result = majorService.saveBatch(majorList);
			return result ? 1 : 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
}
