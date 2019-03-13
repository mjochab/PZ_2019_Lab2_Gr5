package ur.inf.lab2.pz.servicemanmanagement.integration.features;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ur.inf.lab2.pz.servicemanmanagement.ExampleService;
import ur.inf.lab2.pz.servicemanmanagement.domain.MyEntity;
import ur.inf.lab2.pz.servicemanmanagement.repository.ExampleRepository;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ExampleIntegrationTest {

    @Autowired
    private ExampleRepository exampleRepository;

    @Test
    public void x() {
        MyEntity entity = new MyEntity();
        entity.setMsg("hallo");
        MyEntity save = exampleRepository.save(entity);

        assertEquals(save.getMsg(), "hallo");
    }
}
