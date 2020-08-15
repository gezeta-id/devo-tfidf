package net.tinselcity.devo.helpers;

import org.junit.Assert;
import org.junit.Test;

public class StoreTest {

    @Test
    public void shouldStoreFileDataAndCountIt() {
        Store st = new Store("first second third");
        st.addFileData("second", "doc1.txt", 0.045d);

        Assert.assertEquals(1, st.getTotalNumberOfFiles());
        Assert.assertEquals(0.045d, st.getTf("second", "doc1.txt"),0);
        Assert.assertEquals(0f, st.getTf("third", "doc1.txt"),0);
        Assert.assertEquals(0f, st.getTf("second", "doc2.txt"),0);
    }

    //Todo: More extensively test the Store.
    //  - Check that passing a non term is ignored
    //  - Better tests for TotalNumberOfFiles
    //       - if tf passed is 0, it should still increment totalNumberOfFiles
    // etc


}
