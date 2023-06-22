package com.husseinabdallah287.azurefileshare.repository;

import org.springframework.stereotype.Component;

@Component
public class Repository {
    public final String  getBritamInvoiceHeader = "SELECT v.visit_number, v.aggregate_id, v.benefit_id, v.benefit_id, v.benefit_name, " +
            "v.category_id, v.invoice_date, v.hospital_provider_id, v.invoice_number, v.total_invoice_amount, v.member_name, v.member_number FROM claims.visit v where v.visit_number = 48878;";
}
