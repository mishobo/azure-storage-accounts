package com.husseinabdallah287.azurefileshare.repository;

import org.springframework.stereotype.Component;

@Component
public class Repository {
    public final String  getBritamInvoiceHeader = "SELECT v.visit_number, v.aggregate_id, v.benefit_id, v.benefit_id, v.benefit_name, " +
            "v.category_id, date(v.invoice_date) as invoice_date, v.hospital_provider_id, v.invoice_number, v.total_invoice_amount, v.member_name, " +
            "v.member_number, v.payer_id, v.category_id FROM claims.visit v where v.visit_number = ?;";

    public final String getBritamInvoice = "SELECT i.invoice_id, i.invoice_number, i.total_amount FROM claims.invoice i where i.visit_number = 48878;";

    public final String getBritamInvoiceLines = "SELECT * FROM claims.invoice_line where invoice_id = ?;";

    public final String getDiagnosis = "SELECT * FROM claims.diagnosis where visit_number = ?;";
    public final String getProviderMapping = "select p.provider_name, ppm.code, ppm.payer_id from membership.provider p inner join membership.payer_provider_mapping ppm on ppm.provider_id = p.provider_id where p.provider_id = ? and ppm.payer_id = ?;";

    public final String getScheme = "select c.policy_id, py.plan_id, pn.plan_name from membership.category c inner join membership.policy py on py.policy_id = c.policy_id " +
            "inner join membership.plan pn on pn.plan_id = py.plan_id where c.category_id = ?;";
    // SELECT d.file_url FROM claims.document d where d.invoice_number = ?;
    public final String getBritamInvoiceDocument = "SELECT d.file_url FROM claims.document d where d.invoice_number = ? and d.type = \"INVOICE\"";
    public final String getBritamClaimDocument = "SELECT d.file_url FROM claims.document d where d.invoice_number = ? and d.type = \"CLAIM\"";

    public final String updateClaimStatus = "update visit set payer_status = 'SENT', payer_claim_reference = ? where invoice_number=?;";


}
