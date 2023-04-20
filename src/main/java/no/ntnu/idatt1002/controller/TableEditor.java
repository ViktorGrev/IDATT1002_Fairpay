package no.ntnu.idatt1002.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Collection;
import java.util.function.Function;

/**
 * This class acts as a wrapper for a {@link TableView}. It simplifies
 * the process of adding columns and rows to an existing {@link TableView}.
 * @param   <S> the row type
 */
public final class TableEditor<S> {

    private final TableView<S> tableView;

    /**
     * Constructs a new TableBuilder.
     * @param   tableView the target TableView
     */
    public TableEditor(TableView<S> tableView) {
        this.tableView = tableView;
    }

    /**
     * Sets the placeholder text that is visible when
     * the TableView is empty.
     * @param   text the text to display
     * @return  this builder
     */
    public TableEditor<S> setPlaceholder(String text) {
        this.tableView.setPlaceholder(new Label(text));
        return this;
    }

    /**
     * Adds a column to the table with an empty title.
     * @param   valueMapper the value mapper
     * @return  this builder
     * @param   <T> the type of the cell data
     * @see     TableEditor#addColumn(String, Function)
     */
    public <T> TableEditor<S> addColumn(Function<S, T> valueMapper) {
        return addColumn("", valueMapper);
    }

    /**
     * Adds a column to the table with a specified title and value.
     * @param   title the display title of the column
     * @param   valueMapper the value mapper
     * @return  this builder
     * @param   <T> the type of the cell data
     */
    public <T> TableEditor<S> addColumn(String title, Function<S, T> valueMapper) {
        TableColumn<S, T> column = new TableColumn<>(title);
        column.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(valueMapper.apply(data.getValue()))
        );
        tableView.getColumns().add(column);
        return this;
    }

    /**
     * Adds a row to the table.
     * @param   rows the rows to add
     * @return  this builder
     */
    public TableEditor<S> addRow(S rows) {
        tableView.getItems().add(rows);
        return this;
    }

    /**
     * Adds a row to the table.
     * @param   rows the rows to add
     * @return  this builder
     */
    public TableEditor<S> addRows(Collection<S> rows) {
        tableView.getItems().addAll(rows);
        return this;
    }
}
