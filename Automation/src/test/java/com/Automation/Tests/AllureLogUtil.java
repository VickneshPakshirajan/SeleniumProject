package com.Automation.Tests;

import ru.yandex.qatools.allure.annotations.Step;

public final class AllureLogUtil {
	
	private AllureLogUtil() {
    }
    @Step("{0}")
    public static void log(final String message){
        //intentionally empty
    }
}
