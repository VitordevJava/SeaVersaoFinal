package br.com.seatecnologia.hub.core.service;

import br.com.seatecnologia.hub.core.dto.EnderecoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Serviço para consulta de CEP.
 * Este serviço utiliza a API ViaCEP para consultar informações de endereço
 * a partir de um CEP.
 */
@Service
public class CepService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    /*
      Consulta um endereço a partir de um CEP.
      cep o CEP a ser consultado (apenas números)
      retorna o endereço correspondente ao CEP, ou null se não encontrado
     */
    public EnderecoDTO consultarCep(String cep) {
        if (cep == null || cep.isEmpty()) {
            return null;
        }

        // Remove qualquer formatação (hífens)
        String cepSemFormatacao = cep.replaceAll("[^0-9]", "");

        // Verifica se tem exatamente 8 dígitos
        if (cepSemFormatacao.length() != 8) {
            return null;
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            ViaCepResponse response = restTemplate.getForObject(
                    VIA_CEP_URL, 
                    ViaCepResponse.class, 
                    cepSemFormatacao
            );

            if (response != null && response.getCep() != null && !response.getErro()) {
                return new EnderecoDTO(
                        cepSemFormatacao,
                        response.getLogradouro(),
                        response.getBairro(),
                        response.getLocalidade(),
                        response.getUf(),
                        null // complemento deve ser preenchido pelo usuário
                );
            }
        } catch (Exception e) {
            // Em caso de erro na API, retorna null
            return null;
        }

        return null;
    }

    /**
     * Classe interna para mapear a resposta da API ViaCEP.
     */
    private static class ViaCepResponse {
        private String cep;
        private String logradouro;
        private String complemento;
        private String bairro;
        private String localidade;
        private String uf;
        private String ibge;
        private String gia;
        private String ddd;
        private String siafi;
        private Boolean erro = false;

      
        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getComplemento() {
            return complemento;
        }

        public void setComplemento(String complemento) {
            this.complemento = complemento;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getLocalidade() {
            return localidade;
        }

        public void setLocalidade(String localidade) {
            this.localidade = localidade;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public String getIbge() {
            return ibge;
        }

        public void setIbge(String ibge) {
            this.ibge = ibge;
        }

        public String getGia() {
            return gia;
        }

        public void setGia(String gia) {
            this.gia = gia;
        }

        public String getDdd() {
            return ddd;
        }

        public void setDdd(String ddd) {
            this.ddd = ddd;
        }

        public String getSiafi() {
            return siafi;
        }

        public void setSiafi(String siafi) {
            this.siafi = siafi;
        }

        public Boolean getErro() {
            return erro;
        }

        public void setErro(Boolean erro) {
            this.erro = erro;
        }
    }
}