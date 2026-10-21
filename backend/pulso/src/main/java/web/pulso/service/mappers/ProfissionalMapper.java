package web.pulso.service.mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import web.pulso.dto.request.ProfissionalRequestDTO;
import web.pulso.dto.response.profissional.ProfissionalEstatisticaResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleLoginResponseDTO;
import web.pulso.dto.response.profissional.ProfissionalSimpleResponseDTO;
import web.pulso.entity.Profissional;

@Component
public class ProfissionalMapper {

        private final UsuarioMapper usuarioMapper;
        private final InstituicaoMapper instituicaoMapper;
        private final EscalaMapper escalaMapper;
        private final PlantaoMapper plantaoMapper;
        private final TrocaMapper trocaMapper;

        @Autowired
        public ProfissionalMapper(
                        @Lazy UsuarioMapper usuarioMapper,
                        @Lazy InstituicaoMapper instituicaoMapper,
                        EscalaMapper escalaMapper,
                        @Lazy PlantaoMapper plantaoMapper,
                        @Lazy TrocaMapper trocaMapper) {
                this.usuarioMapper = usuarioMapper;
                this.instituicaoMapper = instituicaoMapper;
                this.escalaMapper = escalaMapper;
                this.plantaoMapper = plantaoMapper;
                this.trocaMapper = trocaMapper;
        }

        public Profissional toEntity(ProfissionalRequestDTO dto) {
                if (dto == null)
                        return null;

                var profissional = new Profissional();
                profissional.setNome(dto.getNome());
                profissional.setEmails(dto.getEmails() != null ? new ArrayList<>(dto.getEmails()) : new ArrayList<>());
                profissional.setTelefone(dto.getTelefone());
                profissional.setFotoPerfil(dto.getFotoPerfil());
                profissional.setAnoFormacao(dto.getAnoFormacao());
                profissional.setPix(dto.getPix());
                profissional.setTipoPix(dto.getTipoPix());
                profissional.setCrmNumero(dto.getCrmNumero());
                profissional.setCrms(dto.getCrms() != null ? new HashSet<>(dto.getCrms()) : new HashSet<>());
                profissional.setDataNasc(dto.getDataNasc());
                profissional.setEspecialidades(
                                dto.getEspecialidades() != null ? new HashSet<>(dto.getEspecialidades())
                                                : new HashSet<>());

                return profissional;
        }

        public ProfissionalResponseDTO toResponseDTO(Profissional profissional) {
                if (profissional == null)
                        return null;

                var dto = new ProfissionalResponseDTO();
                dto.setId(profissional.getId());
                dto.setNome(profissional.getNome());
                dto.setEmails(profissional.getEmails() != null ? new ArrayList<>(profissional.getEmails())
                                : new ArrayList<>());
                dto.setTelefone(profissional.getTelefone());
                dto.setFotoPerfil(profissional.getFotoPerfil());
                dto.setAnoFormacao(profissional.getAnoFormacao());
                dto.setPix(profissional.getPix());
                dto.setTipoPix(profissional.getTipoPix());
                dto.setCrmsFormatados(profissional.getCrms() != null ? profissional.getCrms().stream()
                                .map(uf -> profissional.getCrmNumero() + "/" + uf.name()).collect(Collectors.toList())
                                : new ArrayList<>());
                dto.setDataNasc(profissional.getDataNasc());
                dto.setEspecialidades(
                                profissional.getEspecialidades() != null
                                                ? new ArrayList<>(profissional.getEspecialidades())
                                                : new ArrayList<>());

                if (profissional.getUsuario() != null)
                        dto.setUsuario(usuarioMapper.toSimpleResponseDTO(profissional.getUsuario()));

                if (profissional.getInstituicoesClt() != null)
                        dto.setInstituicoesClt(
                                        profissional.getInstituicoesClt().stream()
                                                        .map(instituicaoMapper::toSimpleResponseDTO)
                                                        .collect(Collectors.toList()));

                if (profissional.getInstituicoesPj() != null)
                        dto.setInstituicoesPj(
                                        profissional.getInstituicoesPj().stream()
                                                        .map(instituicaoMapper::toSimpleResponseDTO)
                                                        .collect(Collectors.toList()));

                if (profissional.getInstituicoesLiderEscala() != null)
                        dto.setInstituicoesLiderEscala(
                                        profissional.getInstituicoesLiderEscala().stream()
                                                        .map(instituicaoMapper::toSimpleResponseDTO)
                                                        .collect(Collectors.toList()));

                if (profissional.getEscalas() != null)
                        dto.setEscalas(
                                        profissional.getEscalas().stream()
                                                        .map(escalaMapper::toSimpleResponseDTO)
                                                        .collect(Collectors.toList()));

                if (profissional.getPlantoesCombinados() != null)
                        dto.setPlantoesCombinados(
                                        profissional.getPlantoesCombinados().stream()
                                                        .map(plantaoMapper::toSimpleResponseDTO)
                                                        .collect(Collectors.toList()));

                if (profissional.getPlantoesConfirmados() != null)
                        dto.setPlantoesConfirmados(
                                        profissional.getPlantoesConfirmados().stream()
                                                        .map(plantaoMapper::toSimpleResponseDTO)
                                                        .collect(Collectors.toList()));

                if (profissional.getTrocasOrigem() != null)
                        dto.setTrocasOrigem(
                                        profissional.getTrocasOrigem().stream()
                                                        .map(trocaMapper::toSimpleResponseDTO)
                                                        .collect(Collectors.toList()));

                if (profissional.getTrocasDestino() != null)
                        dto.setTrocasDestino(
                                        profissional.getTrocasDestino().stream()
                                                        .map(trocaMapper::toSimpleResponseDTO)
                                                        .collect(Collectors.toList()));

                return dto;
        }

        public ProfissionalEstatisticaResponseDTO toEstatisticaResponseDTO(Profissional profissional) {
                if (profissional == null)
                        return null;

                var dto = new ProfissionalEstatisticaResponseDTO();
                dto.setId(profissional.getId());
                dto.setNome(profissional.getNome());
                dto.setFoto(profissional.getFotoPerfil());
                dto.setEmails(profissional.getEmails() != null ? new ArrayList<>(profissional.getEmails())
                                : new ArrayList<>());
                dto.setTelefone(profissional.getTelefone());

                return dto;
        }

        public ProfissionalSimpleLoginResponseDTO toSimpleLoginResponseDTO(Profissional profissional) {
                if (profissional == null)
                        return null;

                var dto = new ProfissionalSimpleLoginResponseDTO();
                dto.setId(profissional.getId());
                dto.setNome(profissional.getNome());
                dto.setFotoPerfil(profissional.getFotoPerfil());
                dto.setEspecialidades(
                                profissional.getEspecialidades() != null
                                                ? new ArrayList<>(profissional.getEspecialidades())
                                                : new ArrayList<>());
                return dto;
        }

        public ProfissionalSimpleResponseDTO toSimpleResponseDTO(Profissional profissional) {
                if (profissional == null) {
                        return null;
                }

                var dto = new ProfissionalSimpleResponseDTO();
                dto.setId(profissional.getId());
                dto.setFoto(profissional.getFotoPerfil());
                dto.setNome(profissional.getNome());

                return dto;
        }
}
