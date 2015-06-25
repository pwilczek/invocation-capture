package name.wilu.excel;

import org.junit.Assert;
import org.junit.Test;

public class ExcelColumnTest {

    @Test
    public void shouldHaveFluentInterface() {

        Template template = new Template();

        ExcelColumn
                .from(TestClass.class)
                .in(template)
                .withName("SomeName")
                .call().getId();
    }

    @Test
    public void shouldExtractValue() {
        ExcelColumn<TestClass> col = ExcelColumn.from(TestClass.class);
        col.withName("SomeName").call().getId();
        //
        TestClass test = new TestClass();
        test.setId(1L);
        Assert.assertTrue(col.value(test).equals(1L));
    }

    //
    public static class TestClass {
        Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }


}
