package com.angus.pethomeadoptionbackend.rowMapper;

import com.angus.pethomeadoptionbackend.model.Application;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationRowMapper implements RowMapper<Application> {

    public Application mapRow(ResultSet rs, int rowNum) throws SQLException {

        Application application = new Application();

        application.setApplication_id(rs.getInt("application_id"));
        application.setApply_no(rs.getString("application_no"));
        application.setApplicant_id(rs.getInt("applicant_id"));
        application.setPet_id(rs.getInt("pet_id"));
        application.setApplyStatus(rs.getInt("applyStatus"));
        application.setCreated_date(rs.getDate("created_date"));
        application.setLast_modified_date(rs.getDate("last_modified_date"));
        application.setApproved_by(rs.getInt("approved_by"));

        return application;
    }
}
