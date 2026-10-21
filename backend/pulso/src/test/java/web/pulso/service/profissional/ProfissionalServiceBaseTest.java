package web.pulso.service.profissional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import web.pulso.repository.PlantaoRepository;
import web.pulso.repository.ProfissionalRepository;
import web.pulso.repository.UsuarioRepository;
import web.pulso.service.ProfissionalService;
import web.pulso.service.mappers.ProfissionalMapper;

@ExtendWith(MockitoExtension.class)
public abstract class ProfissionalServiceBaseTest {

    @InjectMocks
    protected ProfissionalService profissionalService;

    @Mock
    protected ProfissionalRepository profissionalRepository;

    @Mock
    protected ProfissionalMapper profissionalMapper;

    @Mock
    protected UsuarioRepository usuarioRepository;

    @Mock
    protected PlantaoRepository plantaoRepository;
}
