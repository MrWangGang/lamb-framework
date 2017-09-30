package com.lamb.lambframework.core.verify;

import com.lamb.lambframework.core.annotation.ParamVerify;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public interface ProcessFoundation {
    public void process(ParamVerify annotation, Object value);
}
