/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import instance.Instance_data;
import instance.Instance_hold;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author RobsonP
 */
public class MyTableCellRenderer extends DefaultTableCellRenderer{
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column){
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (row == Instance_data.getCurrentplay()) {
                cell.setBackground(Color.LIGHT_GRAY);
            }else if ((row%2) == 0 && !isSelected) {
                cell.setBackground(new Color(240,240,240));
            }else if (!isSelected){
                cell.setBackground(new Color(250,250,250));
            }else {
                cell.setBackground(Instance_hold.getMframe().getjTable_playlist().getSelectionBackground());
            }
           
            return cell;
    }
}