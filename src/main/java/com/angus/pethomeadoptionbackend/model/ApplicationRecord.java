package com.angus.pethomeadoptionbackend.model;

import java.util.Date;

public class ApplicationRecord
{
    private Integer application_id;
    private String apply_no;
    private String applicant_id;
    private Integer pet_id;
    private String pet_name;
    private Integer applyStatus;
    private String applyStatus_name;
    private Date created_date;
    private Integer approved_by;

    public Integer getApplication_id() {
        return application_id;
    }

    public void setApplication_id(Integer application_id) {
        this.application_id = application_id;
    }

    public String getApply_no() {
        return apply_no;
    }

    public void setApply_no(String apply_no) {
        this.apply_no = apply_no;
    }

    public String getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(String applicant_id) {
        this.applicant_id = applicant_id;
    }

    public Integer getPet_id() {
        return pet_id;
    }

    public void setPet_id(Integer pet_id) {
        this.pet_id = pet_id;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyStatus_name() {
        return applyStatus_name;
    }

    public void setApplyStatus_name(String applyStatus_name) {
        this.applyStatus_name = applyStatus_name;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Integer getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(Integer approved_by) {
        this.approved_by = approved_by;
    }
}
