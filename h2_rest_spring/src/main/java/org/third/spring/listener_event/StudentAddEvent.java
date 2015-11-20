package org.third.spring.listener_event;

import org.springframework.context.ApplicationEvent;

public class StudentAddEvent extends ApplicationEvent {

    private static final long serialVersionUID = -4193657478326612342L;

    public StudentAddEvent(Object source) {
        super(source);
    }
    
    
    

    /**
     * 学生姓名
     */
    private String m_sStudentName;

    /**
     * @param source
     */
    public StudentAddEvent(Object source, String _sStudentName) {
        super(source);
        this.m_sStudentName = _sStudentName;
    }

    /**
     * 获取学生姓名
     * 
     * @return
     */
    public String getStudentName() {
        return m_sStudentName;
    }
}
