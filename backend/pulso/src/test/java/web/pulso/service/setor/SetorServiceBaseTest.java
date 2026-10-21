package web.pulso.service.setor;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import web.pulso.repository.InstituicaoRepository;
import web.pulso.repository.SetorRepository;
import web.pulso.service.SetorService;
import web.pulso.service.mappers.SetorMapper;

@ExtendWith(MockitoExtension.class)
public abstract class SetorServiceBaseTest {

    @InjectMocks
    protected SetorService setorService;
    
    @Mock
    protected SetorRepository setorRepository;

    @Mock
    protected SetorMapper setorMapper;

    @Mock
    protected InstituicaoRepository instituicaoRepository;
}
