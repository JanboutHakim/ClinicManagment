package com.example.DocLib.models.doctor;

import com.example.DocLib.enums.BillingStatus;
import com.example.DocLib.models.appointment.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the appointment this billing is for
    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillingStatus status; // Enum with PENDING, PAID, CANCELED, etc.

    @Column(nullable = false)
    private String paymentMethod; // Or replace with enum if you prefer

    private LocalDateTime issuedAt;

    private LocalDateTime paidAt;

    private BigDecimal discount; // optional
    private BigDecimal tax; // optional
}
