package com.epam.esm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The GiftCertificate entity.
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "gift_certificates")
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(
            name = "gift_certificate_has_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;

    /**
     * On pre persist action.
     */
    @PrePersist
    public void onPrePersist() {
        log.info("{}: insert new gift certificate", LocalDateTime.now());
    }

    /**
     * On pre update action.
     */
    @PreUpdate
    public void onPreUpdate() {
        log.info("{}: update gift certificate with id={}", LocalDateTime.now(), this.id);
    }

    /**
     * On pre remove action.
     */
    @PreRemove
    public void onPreRemove() {
        log.info("{}: delete gift certificate with id={}", LocalDateTime.now(), this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GiftCertificate that = (GiftCertificate) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName()) &&
                getDescription().equals(that.getDescription()) &&
                getPrice().equals(that.getPrice()) &&
                getDuration().equals(that.getDuration()) &&
                getCreateDate().equals(that.getCreateDate()) &&
                getLastUpdateDate().equals(that.getLastUpdateDate()) &&
                getTags().equals(that.getTags());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = result * 31 + getName().hashCode();
        result = result * 31 + getDescription().hashCode();
        result = result * 31 + getPrice().hashCode();
        result = result * 31 + getDuration().hashCode();
        result = result * 31 + getCreateDate().hashCode();
        result = result * 31 + getLastUpdateDate().hashCode();
        result = result * 31 + getTags().hashCode();
        return result;
    }
}
