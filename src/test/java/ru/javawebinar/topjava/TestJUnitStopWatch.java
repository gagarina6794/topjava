package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

public class TestJUnitStopWatch extends Stopwatch{

    @Override
    protected void finished(long nanos, Description description) {
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
    }
}