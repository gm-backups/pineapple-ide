/*
Copyright (C) 2009 Luís Reis<luiscubal@gmail.com>
Copyright (C) 2009 Serge Humphrey<bob@bobtheblueberry.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package org.gcreator.pineapple.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import org.gcreator.pineapple.editors.SceneEditor;
import org.gcreator.pineapple.formats.Scene.Background;
import org.gcreator.pineapple.validators.ImageValidator;

/**
 * Panel for scene background properties.
 * 
 * @author Serge Humphrey
 */
public final class SceneBackgroundProperties extends javax.swing.JPanel {

    private static final long serialVersionUID = 125046445356L;
    private SceneEditor editor;
    private BackgroundListModel listModel;
    private boolean nochange = false;

    /**
     * Creates new form SceneBackgroundProperties
     *
     * @param e The SceneEditor.
     */
    public SceneBackgroundProperties(SceneEditor e) {
        this.editor = e;
        initComponents();
        backgroundsList.setModel(listModel = new BackgroundListModel());
        backgroundImageCheckBox.setResourceValidator(new ImageValidator());
        backgroundImageCheckBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for (int row : backgroundsList.getSelectedIndices()) {
                    Background b = editor.scene.backgrounds.get(row);
                    b.image = backgroundImageCheckBox.getSelectedFile();
                }
                updateBackgrounds();
            }
        });
    }

    /**
     * Creates new form SceneBackgroundProperties
     *
     * @deprecated Only use for instantating in NetBeans Editor.
     */
    @Deprecated
    public SceneBackgroundProperties() {
        System.err.println("\n\nFATAL ERROR: SceneBackgroundProperties empty constructor\n\n");
        System.exit(1);
    }

    private class BackgroundListModel implements ListModel {

        public Vector<ListDataListener> listeners = new Vector<ListDataListener>();

        public int getSize() {
            return editor.scene.backgrounds.size();
        }

        public Object getElementAt(int index) {
            return new String("Background " + index);
        }

        public void addListDataListener(ListDataListener l) {
            listeners.add(l);
        }

        public void removeListDataListener(ListDataListener l) {
            listeners.remove(l);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        backgroundsList = new javax.swing.JList();
        everythingPanel = new javax.swing.JPanel();
        drawBackgroundImageCheckBox = new javax.swing.JCheckBox();
        backgroundPanel = new javax.swing.JPanel();
        repeatHorizontalCheckBox = new javax.swing.JCheckBox();
        repeatVerticalCheckBox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        backgroundYSpinner = new javax.swing.JSpinner();
        backgroundXSpinner = new javax.swing.JSpinner();
        backgroundImageCheckBox = new org.gcreator.pineapple.gui.ResourceChooser();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();

        backgroundsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                backgroundsListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(backgroundsList);

        drawBackgroundImageCheckBox.setText("<html>Draw Background<br/>Image</html>");
        drawBackgroundImageCheckBox.setEnabled(false);
        drawBackgroundImageCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawBackgroundImageCheckBoxActionPerformed(evt);
            }
        });

        repeatHorizontalCheckBox.setSelected(true);
        repeatHorizontalCheckBox.setText("Repeat Horizontal");
        repeatHorizontalCheckBox.setEnabled(false);
        repeatHorizontalCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repeatHorizontalCheckBoxActionPerformed(evt);
            }
        });

        repeatVerticalCheckBox.setSelected(true);
        repeatVerticalCheckBox.setText("Repeat Vertical");
        repeatVerticalCheckBox.setEnabled(false);
        repeatVerticalCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repeatVerticalCheckBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("X:");
        jLabel3.setEnabled(false);

        jLabel4.setText("Y:");
        jLabel4.setEnabled(false);

        backgroundYSpinner.setEnabled(false);
        backgroundYSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                backgroundYSpinnerStateChanged(evt);
            }
        });

        backgroundXSpinner.setEnabled(false);
        backgroundXSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                backgroundXSpinnerStateChanged(evt);
            }
        });

        backgroundImageCheckBox.setEnabled(false);

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(repeatHorizontalCheckBox)
                    .addComponent(repeatVerticalCheckBox)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(backgroundYSpinner)
                            .addComponent(backgroundXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(backgroundImageCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addComponent(backgroundImageCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(repeatHorizontalCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(repeatVerticalCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backgroundXSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(backgroundYSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout everythingPanelLayout = new javax.swing.GroupLayout(everythingPanel);
        everythingPanel.setLayout(everythingPanelLayout);
        everythingPanelLayout.setHorizontalGroup(
            everythingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(everythingPanelLayout.createSequentialGroup()
                .addGroup(everythingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(drawBackgroundImageCheckBox)
                    .addComponent(backgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        everythingPanelLayout.setVerticalGroup(
            everythingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(everythingPanelLayout.createSequentialGroup()
                .addComponent(drawBackgroundImageCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        addButton.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        addButton.setText("+");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        removeButton.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        removeButton.setText("-");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(removeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(everythingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(everythingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void drawBackgroundImageCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawBackgroundImageCheckBoxActionPerformed
        boolean db = drawBackgroundImageCheckBox.isSelected();
        for (int row : backgroundsList.getSelectedIndices()) {
            Background b = editor.scene.backgrounds.get(row);
            b.drawImage = db;
        }
        for (Component c : backgroundPanel.getComponents()) {
            c.setEnabled(db);
        }
        if (!nochange) {
            editor.setModified(true);
        }
        updateBackgrounds();
}//GEN-LAST:event_drawBackgroundImageCheckBoxActionPerformed

    private void backgroundsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_backgroundsListValueChanged
        synchronized (this) {
            this.nochange = true;
            if (backgroundsList.getSelectedIndex() >= 0) {
                removeButton.setEnabled(true);
                backgroundImageCheckBox.setEnabled(true);
                for (Component c : everythingPanel.getComponents()) {
                    c.setEnabled(true);
                }
                for (int row : backgroundsList.getSelectedIndices()) {
                    Background b = editor.scene.backgrounds.get(row);
                    drawBackgroundImageCheckBox.setSelected(b.drawImage);
                    for (Component c : backgroundPanel.getComponents()) {
                        c.setEnabled(b.drawImage);
                    }
                    repeatHorizontalCheckBox.setSelected(b.hrepeat);
                    repeatVerticalCheckBox.setSelected(b.vrepeat);
                    backgroundXSpinner.setValue(b.x);
                    backgroundYSpinner.setValue(b.y);
                    backgroundImageCheckBox.setSelectedFile(b.image);
                }
            } else {
                removeButton.setEnabled(false);
                backgroundImageCheckBox.setEnabled(false);
                for (Component c : everythingPanel.getComponents()) {
                    c.setEnabled(false);
                }
                for (Component c : backgroundPanel.getComponents()) {
                    c.setEnabled(false);
                }
            }
            this.nochange = false;
        }
    }//GEN-LAST:event_backgroundsListValueChanged

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        editor.scene.backgrounds.add(new Background());
        int i = editor.scene.backgrounds.size() - 1;
        ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, i, i);
        for (ListDataListener l : listModel.listeners) {
            l.contentsChanged(e);
        }
        backgroundsListValueChanged(null);
        updateBackgrounds();
        if (!nochange) {
            editor.setModified(true);
        }
}//GEN-LAST:event_addButtonActionPerformed

    private void repeatHorizontalCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repeatHorizontalCheckBoxActionPerformed
        boolean rh = repeatHorizontalCheckBox.isSelected();
        for (int row : backgroundsList.getSelectedIndices()) {
            Background b = editor.scene.backgrounds.get(row);
            b.hrepeat = rh;
        }
        updateBackgrounds();
        if (!nochange) {
            editor.setModified(true);
        }
    }//GEN-LAST:event_repeatHorizontalCheckBoxActionPerformed

    private void repeatVerticalCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repeatVerticalCheckBoxActionPerformed
        boolean rv = repeatVerticalCheckBox.isSelected();
        for (int row : backgroundsList.getSelectedIndices()) {
            Background b = editor.scene.backgrounds.get(row);
            b.vrepeat = rv;
        }
        updateBackgrounds();
        if (!nochange) {
            editor.setModified(true);
        }
    }//GEN-LAST:event_repeatVerticalCheckBoxActionPerformed

    private void backgroundXSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_backgroundXSpinnerStateChanged
        for (int row : backgroundsList.getSelectedIndices()) {
            Background b = editor.scene.backgrounds.get(row);
            b.x = (Integer) backgroundXSpinner.getValue();
        }
        updateBackgrounds();
        if (!nochange) {
            editor.setModified(true);
        }
    }//GEN-LAST:event_backgroundXSpinnerStateChanged

    private void backgroundYSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_backgroundYSpinnerStateChanged
        for (int row : backgroundsList.getSelectedIndices()) {
            Background b = editor.scene.backgrounds.get(row);
            b.y = (Integer) backgroundYSpinner.getValue();
        }
        updateBackgrounds();
        if (!nochange) {
            editor.setModified(true);
        }
    }//GEN-LAST:event_backgroundYSpinnerStateChanged

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        int[] rows = backgroundsList.getSelectedIndices();
        for (int i = rows.length - 1; i >= 0; i--) {
            int row = rows[i];
            editor.scene.backgrounds.remove(row);
            ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, row, row);
            for (ListDataListener l : listModel.listeners) {
                l.contentsChanged(e);
            }
        }
        backgroundsListValueChanged(null);
        updateBackgrounds();
        if (!nochange) {
            editor.setModified(true);
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void updateBackgrounds() {
        editor.sea.renderBackgroundCache();
        editor.sea.renderCache();
        editor.sea.paint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private org.gcreator.pineapple.gui.ResourceChooser backgroundImageCheckBox;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JSpinner backgroundXSpinner;
    private javax.swing.JSpinner backgroundYSpinner;
    private javax.swing.JList backgroundsList;
    private javax.swing.JCheckBox drawBackgroundImageCheckBox;
    private javax.swing.JPanel everythingPanel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeButton;
    private javax.swing.JCheckBox repeatHorizontalCheckBox;
    private javax.swing.JCheckBox repeatVerticalCheckBox;
    // End of variables declaration//GEN-END:variables
}