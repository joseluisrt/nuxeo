/*
 * Copyright (c) 2006-2011 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nuxeo - initial API and implementation
 *
 * $Id$
 */

package org.nuxeo.ecm.core.api.model;

/**
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 */
public class InvalidPropertyValueException extends PropertyException {

    private static final long serialVersionUID = -7766425583638251741L;

    public InvalidPropertyValueException(String message) {
        super(message);
    }

    public InvalidPropertyValueException(String message, Throwable cause) {
        super(message, cause);
    }

}