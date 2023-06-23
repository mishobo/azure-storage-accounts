package com.husseinabdallah287.azurefileshare.repository;

import org.springframework.stereotype.Component;

@Component
public class Repository {
    public final String  getBritamInvoiceHeader = "SELECT v.visit_number, v.aggregate_id, v.benefit_id, v.benefit_id, v.benefit_name, " +
            "v.category_id, v.invoice_date, v.hospital_provider_id, v.invoice_number, v.total_invoice_amount, v.member_name, " +
            "v.member_number FROM claims.visit v where v.visit_number = ?;";

    public final String getBritamInvoice = "SELECT i.invoice_id, i.invoice_number, i.total_amount FROM claims.invoice i where i.visit_number = 48878;";

    public final String getBritamInvoiceLines = "SELECT * FROM claims.invoice_line where invoice_id = ?;";

    public final String getDiagnosis = "SELECT * FROM claims.diagnosis where visit_number = ?;";
}
