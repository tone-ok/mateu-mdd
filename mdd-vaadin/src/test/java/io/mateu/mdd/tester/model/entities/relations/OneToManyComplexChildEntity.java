package io.mateu.mdd.tester.model.entities.relations;

import io.mateu.mdd.core.annotations.ListColumn;
import io.mateu.mdd.core.annotations.WeekDays;
import lombok.MateuMDDEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.MateuMDDEntity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@MateuMDDEntity
public class OneToManyComplexChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ListColumn
    private String name = "";

    private int age;

    @WeekDays
    private boolean[] weekDays;


    @ManyToOne
    private OneToManyParentEntity parent;





    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean eq = this == obj || (obj != null && obj instanceof OneToManyComplexChildEntity && id == ((OneToManyComplexChildEntity)obj).id);
        if (eq && id == 0) eq = this == obj;
        return eq;
    }

    @Override
    public String toString() {
        return name;
    }
}
