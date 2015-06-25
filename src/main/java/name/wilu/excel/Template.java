package name.wilu.excel;

import java.util.LinkedHashSet;

public class Template {

    private final LinkedHashSet<ExcelColumn<?>> columns = new LinkedHashSet();

    public Template addColumn(ExcelColumn<?> column) {
        columns.add(column);
        return this;
    }
}
