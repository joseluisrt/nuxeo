/*
 * (C) Copyright 2006-2009 Nuxeo SAS (http://nuxeo.com/) and contributors.
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
 *
 * Contributors:
 *     Nuxeo - initial API and implementation
 *
 * $Id$
 */

package org.nuxeo.ecm.platform.convert.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.Blobs;
import org.nuxeo.ecm.core.api.blobholder.BlobHolder;
import org.nuxeo.ecm.core.api.blobholder.SimpleBlobHolder;
import org.nuxeo.ecm.platform.convert.ooomanager.OOoManagerService;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.NXRuntimeTestCase;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public abstract class BaseConverterTest extends Assert {

    private static final Log log = LogFactory.getLog(BaseConverterTest.class);

    final NXRuntimeTestCase tc = new NXRuntimeTestCase();

    OOoManagerService oooManagerService;

    protected static BlobHolder getBlobFromPath(String path, String srcMT) throws IOException {
        File file = FileUtils.getResourceFileFromContext(path);
        assertTrue(file.length() > 0);

        Blob blob = Blobs.createBlob(file);
        if (srcMT != null) {
            blob.setMimeType(srcMT);
        }
        blob.setFilename(file.getName());
        return new SimpleBlobHolder(blob);
    }

    protected static BlobHolder getBlobFromPath(String path) throws IOException {
        return getBlobFromPath(path, null);
    }

    @Before
    public void setUp() throws Exception {
        tc.setUp();
        tc.deployBundle("org.nuxeo.ecm.core.api");
        tc.deployBundle("org.nuxeo.ecm.core.convert.api");
        tc.deployBundle("org.nuxeo.ecm.core.convert");
        tc.deployBundle("org.nuxeo.ecm.core.mimetype");
        tc.deployBundle("org.nuxeo.ecm.platform.convert");

        oooManagerService = Framework.getService(OOoManagerService.class);
        try {
            oooManagerService.startOOoManager();
        } catch (Exception e) {
            log.warn("Can't run OpenOffice, JOD converter will not be available.");
        }
    }

    @After
    public void tearDown() throws Exception {
        oooManagerService = Framework.getService(OOoManagerService.class);
        if (oooManagerService.isOOoManagerStarted()) {
            oooManagerService.stopOOoManager();
        }
        tc.tearDown();
    }

    public static String readPdfText(File pdfFile) throws IOException {
        PDFTextStripper textStripper = new PDFTextStripper();
        PDDocument document = PDDocument.load(pdfFile);
        String text = textStripper.getText(document);
        document.close();
        return text.trim();
    }

    public static boolean isPDFA(File pdfFile) throws Exception {
        PDDocument pddoc = PDDocument.load(pdfFile);
        XMPMetadata xmp = pddoc.getDocumentCatalog().getMetadata().exportXMPMetadata();
        Document doc = xmp.getXMPDocument();
        // <rdf:Description xmlns:pdfaid="http://www.aiim.org/pdfa/ns/id/"
        // rdf:about="">
        // <pdfaid:part>1</pdfaid:part>
        // <pdfaid:conformance>A</pdfaid:conformance>
        // </rdf:Description>
        NodeList list = doc.getElementsByTagName("pdfaid:conformance");
        return list != null && "A".equals(list.item(0).getTextContent());
    }

}
