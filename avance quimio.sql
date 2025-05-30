USE [quimioterapia_ipor]
GO
/****** Object:  Table [dbo].[aseguradora]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[aseguradora](
	[id] [int] NOT NULL,
	[nombre] [nchar](10) NULL,
 CONSTRAINT [PK_aseguradora] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[box]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[box](
	[id] [int] NOT NULL,
	[codigo] [nchar](10) NULL,
 CONSTRAINT [PK_box] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[catalogo_cie]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[catalogo_cie](
	[id] [int] NOT NULL,
	[codigo] [nchar](10) NULL,
	[descripcion] [nchar](10) NULL,
 CONSTRAINT [PK_catalogo_cie] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cita]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cita](
	[id] [int] NOT NULL,
	[fecha] [nchar](10) NOT NULL,
	[hora_programada] [nchar](10) NOT NULL,
	[hora_inicio] [nchar](10) NOT NULL,
	[hora_fin] [nchar](10) NOT NULL,
	[hora_protocolo] [nchar](10) NULL,
	[id_box] [int] NULL,
 CONSTRAINT [PK_cita] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[contratante]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[contratante](
	[id] [int] NOT NULL,
	[nombre] [nchar](10) NULL,
 CONSTRAINT [PK_contratante] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[enfermera]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[enfermera](
	[id] [int] NOT NULL,
	[apellido_p] [nchar](10) NULL,
	[apellido_m] [nchar](10) NULL,
	[nombre] [nchar](10) NULL,
 CONSTRAINT [PK_enfermera] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[funciones_vitales]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[funciones_vitales](
	[id] [int] NOT NULL,
	[id_paciente] [int] NULL,
	[estado] [nchar](10) NULL,
	[fecha] [nchar](10) NULL,
	[hora] [nchar](10) NULL,
	[presion_arterial] [nchar](10) NULL,
	[presion_arterial_max] [nchar](10) NULL,
	[frecuencia_cardiaca] [nchar](10) NULL,
	[temperatura] [nchar](10) NULL,
	[saturacion] [nchar](10) NULL,
	[frecuencia_respiratoria] [nchar](10) NULL,
	[peso] [nchar](10) NULL,
	[talla] [nchar](10) NULL,
	[superficie_corporal] [nchar](10) NULL,
 CONSTRAINT [PK_funciones_vitales] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[medicamento]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[medicamento](
	[id] [int] NOT NULL,
 CONSTRAINT [PK_medicamento] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[medico]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[medico](
	[id] [int] NOT NULL,
	[apellido_p] [nchar](10) NULL,
	[apellido_m] [nchar](10) NULL,
	[nombre] [nchar](10) NULL,
 CONSTRAINT [PK_medico] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[paciente]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[paciente](
	[id] [int] NOT NULL,
	[id_aseguradora] [int] NULL,
	[id_tipo_paciente] [int] NULL,
	[id_contratante] [int] NULL,
	[apellido_p] [nchar](10) NULL,
	[apellido_m] [nchar](10) NULL,
	[nombre] [nchar](10) NULL,
	[fecha_nacimiento] [date] NULL,
	[edad] [int] NULL,
	[sexo] [nchar](10) NULL,
	[id_tipo_doc_identidad] [int] NOT NULL,
	[num_celular] [nchar](10) NULL,
 CONSTRAINT [PK_paciente] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rol]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rol](
	[id] [int] NOT NULL,
	[nombre] [nchar](10) NULL,
 CONSTRAINT [PK_rol] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipo_doc_identidad]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipo_doc_identidad](
	[id] [int] NOT NULL,
	[nombre] [nchar](10) NULL,
 CONSTRAINT [PK_tipo_doc_identidad] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tipo_paciente]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tipo_paciente](
	[id] [int] NOT NULL,
	[nombre] [nchar](10) NULL,
 CONSTRAINT [PK_tipo_paciente] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usuario]    Script Date: 22/05/2025 14:11:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usuario](
	[id] [int] NOT NULL,
	[username] [nchar](10) NULL,
	[password] [nchar](10) NULL,
	[nombre] [nchar](10) NULL,
	[id_rol] [int] NULL,
 CONSTRAINT [PK_usuario] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[cita]  WITH CHECK ADD  CONSTRAINT [FK_cita_box] FOREIGN KEY([id_box])
REFERENCES [dbo].[box] ([id])
GO
ALTER TABLE [dbo].[cita] CHECK CONSTRAINT [FK_cita_box]
GO
ALTER TABLE [dbo].[funciones_vitales]  WITH CHECK ADD  CONSTRAINT [FK_funciones_vitales_paciente] FOREIGN KEY([id_paciente])
REFERENCES [dbo].[paciente] ([id])
GO
ALTER TABLE [dbo].[funciones_vitales] CHECK CONSTRAINT [FK_funciones_vitales_paciente]
GO
ALTER TABLE [dbo].[paciente]  WITH CHECK ADD  CONSTRAINT [FK_paciente_aseguradora] FOREIGN KEY([id_aseguradora])
REFERENCES [dbo].[aseguradora] ([id])
GO
ALTER TABLE [dbo].[paciente] CHECK CONSTRAINT [FK_paciente_aseguradora]
GO
ALTER TABLE [dbo].[paciente]  WITH CHECK ADD  CONSTRAINT [FK_paciente_contratante] FOREIGN KEY([id_contratante])
REFERENCES [dbo].[contratante] ([id])
GO
ALTER TABLE [dbo].[paciente] CHECK CONSTRAINT [FK_paciente_contratante]
GO
ALTER TABLE [dbo].[paciente]  WITH CHECK ADD  CONSTRAINT [FK_paciente_tipo_doc_identidad] FOREIGN KEY([id_tipo_doc_identidad])
REFERENCES [dbo].[tipo_doc_identidad] ([id])
GO
ALTER TABLE [dbo].[paciente] CHECK CONSTRAINT [FK_paciente_tipo_doc_identidad]
GO
ALTER TABLE [dbo].[paciente]  WITH CHECK ADD  CONSTRAINT [FK_paciente_tipo_paciente] FOREIGN KEY([id_tipo_paciente])
REFERENCES [dbo].[tipo_paciente] ([id])
GO
ALTER TABLE [dbo].[paciente] CHECK CONSTRAINT [FK_paciente_tipo_paciente]
GO
ALTER TABLE [dbo].[usuario]  WITH CHECK ADD  CONSTRAINT [FK_usuario_rol] FOREIGN KEY([id_rol])
REFERENCES [dbo].[rol] ([id])
GO
ALTER TABLE [dbo].[usuario] CHECK CONSTRAINT [FK_usuario_rol]
GO
