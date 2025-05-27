package com.example.DocLib.models.specification;

import com.example.DocLib.models.patient.Patient;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecification {

    public static Specification<Patient> nameLike(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Patient> ageEqual(Integer age) {
        return (root, query, cb) -> age == null ? null : cb.equal(root.get("age"), age);
    }

    public static Specification<Patient> addressLike(String address) {
        return (root, query, cb) -> address == null ? null : cb.like(root.get("address"), "%" + address + "%");
    }
}