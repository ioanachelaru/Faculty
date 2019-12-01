package utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import repo.RepoAngajat;
import repo.RepoInscriere;
import repo.RepoParticipant;
import repo.RepoProba;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class ConcursConfig {
    @Bean
    @Primary
    public Properties jdbcProps(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(getClass().getResourceAsStream("/bd.properties"));
            System.out.println("Properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        return  serverProps;
    }

    @Bean(name="repoAngajat")
    public RepoAngajat createRepoAngajat(Properties jdbcProps){ return new RepoAngajat(jdbcProps); }

    @Bean(name="repoParticipant")
    public RepoParticipant createRepoParticipant(Properties jdbcProps){ return new RepoParticipant(jdbcProps); }

    @Bean(name="repoProba")
    public RepoProba createRepoProba(Properties jdbcProps){ return new RepoProba(jdbcProps); }

    @Bean(name="repoParticipant")
    public RepoInscriere createRepoInscriere(Properties jdbcProps){ return new RepoInscriere(jdbcProps); }
}
