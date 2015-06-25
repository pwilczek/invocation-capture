package name.wilu.excel;


public class ExcelColumn<T> {

    private final MethodCall<T> call;
    //
    private String name;
    //
    private ExcelColumn(MethodCall<T> call) {
        this.call = call;
    }

    public static <T> ExcelColumn<T> from(Class<T> clazz) {
        return new ExcelColumn(MethodCall.on(clazz));
    }

    public T apply(T target, Object value) {
        return call.apply(target, value);
    }

    public String value(T source) {
        return call.value(source).toString();
    }


    public ExcelColumn<T> withName(String name) {
        this.name = name;
        return this;
    }

    public T call() {
        return call.target();
    }


    public ExcelColumn<T> in(Template template) {
        template.addColumn(this);
        return this;
    }
}