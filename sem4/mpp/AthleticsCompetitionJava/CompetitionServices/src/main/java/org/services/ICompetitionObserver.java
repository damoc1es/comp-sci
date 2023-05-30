package org.services;

import org.model.AllowedGroup;
import org.model.Child;
import org.model.Event;
import org.model.exception.CompException;

public interface ICompetitionObserver {
    void childRegistered(Child child, AllowedGroup group) throws CompException;
}
