package com.stayscape.backend.domain.place.property.booking.payment

import com.stayscape.backend.domain.AbstractJpaEntityVersioned
import com.stayscape.backend.domain.place.property.booking.Booking
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "payments")
class Payment(
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: PaymentStatus = PaymentStatus.UNFINISHED,

    @Column(name = "payment_date")
    val paymentDate: Instant? = null,

    @Column(name = "transaction_id")
    val transactionId: String? = null,

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id")
    val booking: Booking? = null
) : AbstractJpaEntityVersioned() {
}