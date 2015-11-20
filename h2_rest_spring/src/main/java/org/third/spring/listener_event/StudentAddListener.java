package org.third.spring.listener_event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class StudentAddListener implements ApplicationListener<ApplicationEvent> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.context.ApplicationListener#onApplicationEvent(org
     * .springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent _event) {
        // 1.判断是否是增加学生对象的事件
        if (!(_event instanceof StudentAddEvent)) {
            System.err.println("Not student: " + _event);
            return;
        }

        // 2.是增加学生事件的对象，进行逻辑处理，比如记日志、积分等
        StudentAddEvent studentAddEvent = (StudentAddEvent) _event;
        System.out.println("增加了学生:::" + studentAddEvent.getStudentName());
    }

}