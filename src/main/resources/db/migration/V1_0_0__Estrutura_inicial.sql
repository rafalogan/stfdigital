CREATE SCHEMA plataforma;
CREATE SCHEMA corporativo;
CREATE SCHEMA autuacao;

create table autuacao.classe (sig_classe varchar(255) not null, nom_classe varchar(255), primary key (sig_classe));
create table autuacao.ministro (seq_ministro bigint not null, nom_ministro varchar(255) not null, primary key (seq_ministro));
create table autuacao.peticao (seq_peticao bigint not null, num_ano_peticao smallint not null, sig_classe varchar(255) not null, sig_classe_sugerida varchar(255), dsc_motivo_recusa varchar(255), num_peticao bigint not null, tip_status_peticao varchar(255), primary key (seq_peticao));
create table autuacao.peticao_documento (seq_peticao bigint not null, seq_documento bigint not null, primary key (seq_peticao, seq_documento));
create table autuacao.peticao_parte (seq_peticao_parte bigint not null, seq_pessoa bigint not null, tip_polo varchar(255), seq_peticao bigint, primary key (seq_peticao_parte));
create table autuacao.processo (seq_processo bigint not null, sig_classe varchar(255) not null, seq_ministro_relator bigint, num_processo bigint not null, seq_peticao bigint not null, seq_pprocesso bigint, primary key (seq_processo));
create table autuacao.processo_documento (seq_processo bigint not null, seq_documento bigint not null, primary key (seq_processo, seq_documento));
create table autuacao.processo_parte (seq_processo_parte bigint not null, seq_pessoa bigint not null, tip_polo varchar(255), seq_processo bigint, primary key (seq_processo_parte));
create table corporativo.documento (seq_documento bigint not null, bin_conteudo blob, primary key (seq_documento));
create table corporativo.pessoa (seq_pessoa bigint not null, nom_pessoa varchar(255) not null, primary key (seq_pessoa));
create table plataforma.process_instance_peticao (num_process_instance bigint not null, seq_peticao bigint not null, primary key (num_process_instance));

alter table autuacao.peticao add constraint UK_PETI_NUM_PETICAO  unique (num_peticao, num_ano_peticao);
alter table autuacao.processo add constraint UK_PROC_SIG_CLASSE  unique (sig_classe, num_processo);
alter table autuacao.peticao_documento add constraint FK_PETICAO_PEDO foreign key (seq_peticao) references AUTUACAO.peticao;
alter table autuacao.peticao_parte add constraint FK_PETICAO_PEPA foreign key (seq_peticao) references AUTUACAO.peticao;
alter table autuacao.processo_documento add constraint FK_PROCESSO_PRDO foreign key (seq_processo) references AUTUACAO.processo;
alter table autuacao.processo_parte add constraint FK_PROCESSO_PRPA foreign key (seq_processo) references AUTUACAO.processo;
alter table plataforma.process_instance_peticao add constraint FK_PETICAO_PEPI foreign key (seq_peticao) references AUTUACAO.peticao;

create sequence autuacao.SEQ_MINISTRO;
create sequence autuacao.SEQ_PETICAO;
create sequence autuacao.SEQ_PETICAO_PARTE;
create sequence autuacao.SEQ_PROCESSO;
create sequence autuacao.SEQ_PROCESSO_PARTE;
create sequence corporativo.SEQ_DOCUMENTO;
create sequence corporativo.SEQ_PESSOA;