package jp.ken.rental.common.validator.groups;

import jakarta.validation.GroupSequence;

@GroupSequence({ValidGroup1.class,ValidGroup2.class,ValidGroup3.class})
public interface ValidGroupOrder {

}
