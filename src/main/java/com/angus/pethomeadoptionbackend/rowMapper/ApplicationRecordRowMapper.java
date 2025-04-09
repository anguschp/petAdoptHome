package com.angus.pethomeadoptionbackend.rowMapper;

import com.angus.pethomeadoptionbackend.model.ApplicationRecord;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationRecordRowMapper implements RowMapper<ApplicationRecord> {
    @Override
    public ApplicationRecord mapRow(ResultSet rs, int rowNum) throws SQLException {

        ApplicationRecord record = new ApplicationRecord();
        record.setApplication_id(rs.getInt("application_id"));
        record.setApply_no(rs.getString("apply_no"));
        record.setApplicant_id(rs.getString("applicant_id"));
        record.setPet_id(rs.getInt("pet_id"));
        record.setPet_name(rs.getString("pet_name"));
        record.setApplyStatus(rs.getInt("applyStatus"));
        record.setApplyStatus_name(rs.getString("statusName"));
        record.setCreated_date(rs.getDate("created_date"));
        record.setApproved_by(rs.getInt("approved_by"));

        return record;
    }
}
