package med.voll.medvollapi.controller;

import jakarta.validation.Valid;
import med.voll.medvollapi.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

//RestController para aplicações Rest
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico) {
        medicoRepository.save(new Medico(dadosCadastroMedico));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(Pageable pageable) {
        return medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dadosAtualizacaoMedico) {
        var medico = medicoRepository.getReferenceById(dadosAtualizacaoMedico.id());
        medico.atualizarInformacoes(dadosAtualizacaoMedico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void desativarMedico(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }
}
