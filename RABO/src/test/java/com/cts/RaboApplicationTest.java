package com.cts;

import com.cts.controller.TransactionControllerTest;
import com.cts.service.TransactionServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TransactionControllerTest.class,
        TransactionServiceImplTest.class
})

public class RaboApplicationTest {


}
