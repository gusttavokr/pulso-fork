package web.pulso.service.instituicao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import web.pulso.repository.InstituicaoRepository;
import web.pulso.repository.ProfissionalRepository;
import web.pulso.repository.UsuarioRepository;
import web.pulso.service.InstituicaoService;
import web.pulso.service.mappers.InstituicaoMapper;

@ExtendWith(MockitoExtension.class)
public abstract class InstituicaoServiceBaseTest {

    @InjectMocks
    protected InstituicaoService instituicaoService;

    @Mock
    protected InstituicaoRepository instituicaoRepository;

    @Mock
    protected InstituicaoMapper instituicaoMapper;

    @Mock
    protected ProfissionalRepository profissionalRepository;

    @Mock
    protected UsuarioRepository usuarioRepository;
}
