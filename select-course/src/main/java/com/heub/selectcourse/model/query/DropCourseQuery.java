package com.heub.selectcourse.model.query;

/**
 * @author qqz
 * @date 2024/10/6
 * <p>
 * description
 */

import lombok.Data;

@Data
public class DropCourseQuery {

    private Long classId;

    private String studentNumber;

}
