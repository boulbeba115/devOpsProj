package com;

import com.iit.demo.util.CalculatorUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SomthingIT {

    @Test
    public void sumShouldReturnCorrectSum(){
        assertThat(CalculatorUtil.sum(1,3)).isEqualTo(4);

    }


}
