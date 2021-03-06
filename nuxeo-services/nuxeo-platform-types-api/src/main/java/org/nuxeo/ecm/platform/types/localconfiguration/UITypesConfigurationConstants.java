/*
 * (C) Copyright 2010 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * Contributors:
 * Nuxeo - initial API and implementation
 */

package org.nuxeo.ecm.platform.types.localconfiguration;

/**
 * @author <a href="mailto:troger@nuxeo.com">Thomas Roger</a>
 */
public class UITypesConfigurationConstants {

    private UITypesConfigurationConstants() {
        // Constants class
    }

    public static final String UI_TYPES_CONFIGURATION_FACET = "UITypesLocalConfiguration";

    public static final String UI_TYPES_CONFIGURATION_ALLOWED_TYPES_PROPERTY = "uitypesconf:allowedTypes";

    public static final String UI_TYPES_CONFIGURATION_DENIED_TYPES_PROPERTY = "uitypesconf:deniedTypes";

    public static final String UI_TYPES_CONFIGURATION_DENY_ALL_TYPES_PROPERTY = "uitypesconf:denyAllTypes";

    public static final String UI_TYPES_CONFIGURATION_DEFAULT_TYPE = "uitypesconf:defaultType";

    public static final String UI_TYPES_DEFAULT_TYPE = "File";

    public static final String UI_TYPES_DEFAULT_NEEDED_SCHEMA = "file";

}
