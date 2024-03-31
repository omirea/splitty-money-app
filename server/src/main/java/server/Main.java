/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EntityScan(basePackages = { "commons", "server" })
public class Main {

    private EmailService emailService;

    public Main(EmailService emailService){
        this.emailService=emailService;
    }

    public static void main(String[] args) {
        ApplicationContext context= SpringApplication.run(Main.class, args);
        Main app= context.getBean(Main.class);
        app.Run();
    }

    private void Run(){
        emailService.sendEmail("egeyarar@gmail.com","deneme","deneme");
    }
}