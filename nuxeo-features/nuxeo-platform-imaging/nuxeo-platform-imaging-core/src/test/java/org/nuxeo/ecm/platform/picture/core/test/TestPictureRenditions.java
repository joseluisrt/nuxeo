/*
 * (C) Copyright 2015 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Thomas Roger
 */

package org.nuxeo.ecm.platform.picture.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.ecm.platform.rendition.Rendition;
import org.nuxeo.ecm.platform.rendition.service.RenditionDefinition;
import org.nuxeo.ecm.platform.rendition.service.RenditionService;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.LocalDeploy;
import org.nuxeo.runtime.test.runner.RuntimeHarness;

import com.google.inject.Inject;

/**
 * @since 7.2
 */
@RunWith(FeaturesRunner.class)
@Features({ CoreFeature.class })
@Deploy({ "org.nuxeo.ecm.platform.commandline.executor", "org.nuxeo.ecm.automation.core",
        "org.nuxeo.ecm.core.mimetype", "org.nuxeo.ecm.actions", "org.nuxeo.ecm.platform.picture.api",
        "org.nuxeo.ecm.platform.picture.core", "org.nuxeo.ecm.platform.picture.convert",
        "org.nuxeo.ecm.platform.rendition.api", "org.nuxeo.ecm.platform.rendition.core" })
@LocalDeploy("org.nuxeo.ecm.platform.picture.core:OSGI-INF/imaging-listeners-override.xml")
public class TestPictureRenditions {

    public static final List<String> EXPECTED_ALL_RENDITION_DEFINITION_NAMES = Arrays.asList("xmlExport", "zipExport",
            "zipTreeExport", "Thumbnail", "Small", "Medium", "OriginalJpeg");

    public static final List<String> EXPECTED_FILTERED_RENDITION_DEFINITION_NAMES = Arrays.asList("xmlExport",
            "zipExport", "zipTreeExport", "Small", "OriginalJpeg");

    @Inject
    protected CoreSession session;

    @Inject
    protected RenditionService renditionService;

    @Inject
    protected RuntimeHarness runtimeHarness;

    @Test
    public void shouldExposeAllPictureViewsAsRenditions() throws IOException {
        DocumentModel doc = session.createDocumentModel("/", "picture", "Picture");
        doc = session.createDocument(doc);

        List<RenditionDefinition> availableRenditionDefinitions = renditionService.getAvailableRenditionDefinitions(doc);
        assertEquals(7, availableRenditionDefinitions.size());
        for (RenditionDefinition definition : availableRenditionDefinitions) {
            assertTrue(EXPECTED_ALL_RENDITION_DEFINITION_NAMES.contains(definition.getName()));
        }

        List<Rendition> availableRenditions = renditionService.getAvailableRenditions(doc);
        assertEquals(7, availableRenditions.size());
        // they are all visible
        availableRenditions = renditionService.getAvailableRenditions(doc, true);
        assertEquals(7, availableRenditions.size());
    }

    @Test
    public void shouldExposeOnlyMarkedPictureViewsAsRenditions() throws Exception {
        DocumentModel doc = session.createDocumentModel("/", "picture", "Picture");
        doc = session.createDocument(doc);

        List<RenditionDefinition> availableRenditionDefinitions = renditionService.getAvailableRenditionDefinitions(doc);
        assertEquals(7, availableRenditionDefinitions.size());

        runtimeHarness.deployContrib("org.nuxeo.ecm.platform.picture.core",
                "OSGI-INF/imaging-picture-renditions-override.xml");

        availableRenditionDefinitions = renditionService.getAvailableRenditionDefinitions(doc);
        assertEquals(5, availableRenditionDefinitions.size());
        for (RenditionDefinition definition : availableRenditionDefinitions) {
            assertTrue(EXPECTED_FILTERED_RENDITION_DEFINITION_NAMES.contains(definition.getName()));
        }

        List<Rendition> availableRenditions = renditionService.getAvailableRenditions(doc);
        assertEquals(5, availableRenditions.size());
        availableRenditions = renditionService.getAvailableRenditions(doc, true);
        assertEquals(4, availableRenditions.size());

        runtimeHarness.undeployContrib("org.nuxeo.ecm.platform.picture.core",
                "OSGI-INF/imaging-picture-renditions-override.xml");
    }
}
