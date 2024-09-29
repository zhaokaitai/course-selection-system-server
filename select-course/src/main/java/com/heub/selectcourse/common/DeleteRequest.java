package com.heub.selectcourse.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用删除请求
 *
 * @author 秦乾正
 */
@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = -5860707094194210842L;

    private long id;
}
