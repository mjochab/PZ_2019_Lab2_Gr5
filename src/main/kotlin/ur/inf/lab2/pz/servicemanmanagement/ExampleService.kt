package ur.inf.lab2.pz.servicemanmanagement

import org.springframework.stereotype.Service

@Service
class ExampleService {

    fun log(msg: String) = println("$msg - FROM KOTLIN FILE !")

}
