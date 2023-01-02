package com.github.framework.sensitive.test.core.util;

import com.github.framework.sensitive.core.util.BeanUtil;
import com.github.framework.sensitive.test.core.DataPrepareTest;
import com.github.framework.sensitive.test.model.sensitive.entry.UserGroup;
import org.junit.Assert;
import org.junit.Test;


public class BeanUtilTest {

    @Test
    public void deepCopyTest() {
        final UserGroup userGroup = DataPrepareTest.buildUserGroup();
        final UserGroup copyUserGroup = BeanUtil.deepCopy(userGroup);

        Assert.assertEquals(copyUserGroup.toString(), userGroup.toString());
    }

}
