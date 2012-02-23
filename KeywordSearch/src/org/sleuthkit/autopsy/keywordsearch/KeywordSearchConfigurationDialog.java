/*
 * Autopsy Forensic Browser
 * 
 * Copyright 2011 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * KeywordSearchConfigurationDialog.java
 *
 * Created on Feb 10, 2012, 4:32:33 PM
 */
package org.sleuthkit.autopsy.keywordsearch;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.sleuthkit.autopsy.ingest.IngestManager;

/**
 *
 * @author dfickling
 */
public class KeywordSearchConfigurationDialog extends javax.swing.JDialog {

    KeywordSearchListsManagementPanel listsManagementPanel;
    KeywordSearchEditListPanel editListPanel;
    private final static Logger logger = Logger.getLogger(KeywordSearchConfigurationDialog.class.getName());

    /** Creates new form KeywordSearchConfigurationDialog */
    public KeywordSearchConfigurationDialog(String title) {
        super(new JFrame(title), title, true);
        setResizable(false);
        initComponents();
        customizeComponents();
    }

    private void customizeComponents() {
        listsManagementPanel = KeywordSearchListsManagementPanel.getDefault();
        editListPanel = KeywordSearchEditListPanel.getDefault();

        listsManagementPanel.addListSelectionListener(KeywordSearchEditListPanel.getDefault());
        mainSplitPane.setLeftComponent(listsManagementPanel);
        mainSplitPane.setRightComponent(editListPanel);
        mainSplitPane.revalidate();
        mainSplitPane.repaint();

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

        // set the popUp window / JFrame
        int w = this.getSize().width;
        int h = this.getSize().height;

        // set the location of the popUp Window on the center of the screen
        setLocation((screenDimension.width - w) / 2, (screenDimension.height - h) / 2);
        this.pack();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        applyButton = new javax.swing.JButton();
        mainSplitPane = new javax.swing.JSplitPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        applyButton.setText(org.openide.util.NbBundle.getMessage(KeywordSearchConfigurationDialog.class, "KeywordSearchConfigurationDialog.applyButton.text")); // NOI18N
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        mainSplitPane.setDividerSize(8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(331, Short.MAX_VALUE)
                .addComponent(applyButton)
                .addContainerGap())
            .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        editListPanel.save();
        KeywordSearchListsXML loader = KeywordSearchListsXML.getCurrent();
        KeywordSearchIngestService service = KeywordSearchIngestService.getDefault();
        if (IngestManager.getDefault().isServiceRunning(service)) {
            for (KeywordSearchList list : loader.getListsL()) {
                if (list.getUseForIngest()) {
                    service.addToKeywordLists(list.getName());
                }
            }
        }
        this.dispose();
    }//GEN-LAST:event_applyButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyButton;
    private javax.swing.JSplitPane mainSplitPane;
    // End of variables declaration//GEN-END:variables

}
