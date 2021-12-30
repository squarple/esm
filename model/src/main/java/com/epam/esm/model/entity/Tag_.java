package com.epam.esm.model.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The metamodel for Tag entity.
 */
@StaticMetamodel(Tag.class)
public class Tag_ {
    public static volatile SingularAttribute<Tag, Long> id;
    public static volatile SingularAttribute<Tag, String> name;
}
