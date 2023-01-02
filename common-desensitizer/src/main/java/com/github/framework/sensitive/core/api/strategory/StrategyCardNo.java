package com.github.framework.sensitive.core.api.strategory;

import com.github.framework.sensitive.api.IContext;
import com.github.framework.sensitive.api.IStrategy;
import com.github.framework.sensitive.core.util.strategy.SensitiveStrategyUtils;
import com.github.houbb.heaven.util.lang.ObjectUtil;



public class StrategyCardNo implements IStrategy {


    @Override
    public Object des(final Object original, final IContext context) {
        return SensitiveStrategyUtils.cardNo(ObjectUtil.objectToString(original));
    }

}
