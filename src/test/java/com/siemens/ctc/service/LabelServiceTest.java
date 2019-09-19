package com.siemens.ctc.service;

import com.siemens.ctc.model.Label;
import io.jsonwebtoken.lang.Collections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelServiceTest {

    @Resource
    private LabelService labelService;

    @Test
    public void addLabel() {
        // 标签存在, 数量为1
        labelService.addLabel("event");
        List<Label> labelList = labelService.getLabelNameAndNum();
        for (Label label : labelList) {
            if (label.getLabelName().equals("event")) {
                Assert.assertEquals(2, label.getLabelNum());
            }
        }

        // 标签不存在
        labelService.addLabel("siemens");
        List<Label> labelList1 = labelService.getLabelNameAndNum();
        for (Label label : labelList1) {
            if (label.getLabelName().equals("siemens")) {
                Assert.assertEquals(1, label.getLabelNum());
                Assert.assertEquals("siemens", label.getLabelName());
            }
        }
    }

    @Test
    public void getLabels() {
        List<Label> labelList = labelService.getLabels(1);
        Assert.assertEquals(3, Collections.size(labelList));
    }

    @Test
    public void isLabelExist() {
        // 标签存在
        Assert.assertTrue(labelService.isLabelExist("event"));
        // 标签不存在
        Assert.assertTrue(!(labelService.isLabelExist("xxxx")));
    }

    @Test
    public void plusLabelNum() {
        labelService.plusLabelNum("");
    }

    @Test
    public void minusLabelNum() {
    }

    @Test
    public void addLabels() {
        labelService.addLabels(1, "siemens");
        List<Label> labelList = labelService.getLabelNameAndNum();
        for (Label label : labelList) {
            if (label.getLabelName().equals("siemens")) {
                Assert.assertEquals(2, label.getLabelNum());
            }
        }
    }

    @Test
    public void deleteLabel() {
    }

    @Test
    public void getLabelNameAndNum() {
    }

    @Test
    public void addLabelMap() {
    }

    @Test
    public void deleteLabelMap() {
    }

    @Test
    public void labelSearch() {
    }
}