<%@ taglib uri="/core" prefix="c"%>
<%@ taglib uri="functions" prefix="f"%>
      <div align="center">
        <table width="100%" border="0" cellspacing="2" height="100%">
          <tr>
            <td>
              <p>Sistema de banco de dados de imagens de aves do Brasil, versão 02.00</p>
              <hr>
              <p>Seja bem-vindo ao <b>Sistema de Informações de Aves do Brasil</b>. </p>
              <p>O objetivo deste projeto é disponibilizar um meio fácil para que interessados em aves possam
              compartilhar suas fotos, ou visualizar fotos de outros autores, tornando o site um banco de dados
              de imagens das aves do Brasil.</p>
              <p>Sugestões, críticas e idéias são muito bem-vindas. Envie seus comentários para o email 
              <a href="mailto:webmaster@aves.brasil.nom.br">webmaster</a></p>
              <b>O site segue a nomenclatura e a lista do CBRO </b>
              <br>
              <br>
              <br>
            
              <hr>
              <b>Usuários registrados: ${f:numberOfUsers()}</b><br>
              <b>Fotos no site: ${f:numberOfPhotos()}</b><br>
              <b>Famílias com fotos: ${f:numberOfFamilies()}</b><br>
              <b>Espécies com fotos: ${f:numberOfSpecies()}</b>
              <br>
              <br>
              <hr>
            </td>
          </tr>
          <tr>
            <td>
              <div align="center">
              <img src="/jsp/images/PicaPauzinho.jpg" width="160" height="107" align="bottom">
              <img src="/jsp/images/PicaPauzinho1.jpg" width="160" height="107" align="bottom">
              <img src="/jsp/images/PicaPauzinho2.jpg" width="160" height="107" align="bottom">
              </div>
            </td>
          </tr>
        </table>
      </div>
