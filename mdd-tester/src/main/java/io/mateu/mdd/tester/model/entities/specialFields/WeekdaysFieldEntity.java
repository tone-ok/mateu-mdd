package io.mateu.mdd.tester.model.entities.specialFields;

import io.mateu.mdd.core.annotations.WeekDays;
import io.mateu.mdd.core.model.common.File;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter@Setter
public class WeekdaysFieldEntity {

    @Id@GeneratedValue
    private long id;

    private String name;

    @WeekDays
    private boolean[] weekDays;

}
