							     +-------------------------+
							     | 1DV008 Timeline project |
							     +-------------------------+





											+---------------------------------------+
                                                                                    +---+Event         	                        |
										    |   +---------------------------------------+		   +------------------------------------------+
										    |   +Variables:                             |      +-----------+Timeline                                  |
										    |   +---------------------------------------+      |	   +------------------------------------------+
										    |   |(int)id                                |      |	   |Variables:                                |
										    |   |(String)created                        |      |	   +------------------------------------------+
										    |   |(String)modified                       |      |	   |(String)url                               |
										    |   |(String)event_title                    |      | 	   |(int)id                                   |
										    |   |(String)event_description              |      | 	   |(int)user                                 |
										    |   |(String)event_start_datetime           |      | 	   |(String)timeline_title                    |
										    |   |(String)event_stop_datetime            |      | 	   |(String)timeline_description              |
										    |   |(int)timeline                          |      | 	   |(String)timeline_start_datetime           |
										    |   +---------------------------------------+      | 	   |(String)timeline_stop_datetime            |
										    |   |Functions:                             |      | 	   |(String)created                           |
										    |   +---------------------------------------+      | 	   |(String)modified                          |
										    |   |(int)getId                             |      |  	   +------------------------------------------+
										    |   |(void)setId                            |      |  	   |Functions:                                |
										    |   |(String)getCreated                     |      |  	   +------------------------------------------+
										    |   |(void)setCreated                       |      |  	   |(String)getUrl                            |
										    |   |(String)getModified                    |      |  	   |(void)setUrl                              |
										    |   |(void)setModified                      |      |  	   |(int)getId                                |
										    |   |(String)getEvent_title                 |      |  	   |(void)setId                               |
										    |   |(void)setEvent_title                   |      |  	   |(int)getUser                              |
										    |   |(String)getEvent_description           |      |  	   |(void)setUser                             |
										    |   |(void)setEvent_description             |      |  	   |(String)getTimeline_title                 |
										    |   |(LocalDateTime)getEvent_start_datetime |      |  	   |(void)setTimeline_title                   |
							 			    |   |(void)setEvent_start_datetime          |      |  	   |(String)getTimeline_description           |
							 			    |   |(LocalDateTime)getEvent_stop_datetime  |      |  	   |(void)setTimeline_description             |
							    +-----------------+	    |   |(void)setEvent_stop_datetime           |      |  	   |(LocalDateTime)getTimeline_start_datetime |
			   +-------------------+	+---+ APIConfigReader |	    |   |(int)getTimeline                       |      |  	   |(void)setTimeline_start_datetime          |
		        +--+ API               |        |   +-----------------+	    |   |(void)setTimeline                      |      |  	   |(LocalDateTime)getTimeline_stop_datetime  |
		        |  +-------------------+        |   | Functions:      |	    |   +---------------------------------------+      |  	   |(void)setTimeline_stop_datetime           |
		        |  | Variables:        |        |   +-----------------+	    |						       | 	   |(String)getCreated                        |
		        |  +-------------------+        |   | (API)read       |	    |						       | 	   |(void)setCreated                          |
		        |  | (String)host      |        |   | (void)write     |	    +-----------------------+			       | 	   |(String)getModified                       |
		        |  | (String)port      |        |   +-----------------+				    |			       | 	   |(void)setModified                         |
		        |  +-------------------+	|						    |			       |	   +------------------------------------------+
		        |  | Functions:        |        |						    |			       |
		        |  +-------------------+	+---------------------------------------------+	    |	    +------------------+
		        |  | (String)getHost   |						      |	    |	    |
		        |  | (void)setHost     |						      |	    |	    |
		        |  | (String)getPort   |						      |	    |	    |
		        |  | (void)setPort     |						      |	    |	    |					     +--------------------------------+
		        |  +-------------------+						      |	    |	    | 	    +--------------------------------+User                            |
		        | 									      |	    |	    |	    |                 		     +--------------------------------+
		        +-------------------------------------------------------------------------+   |	    |	    |	    | 				     |Variables:                      |
					                                         		+-+---+-----+-------+-------+-------------+		     +--------------------------------+
												| SessionHandler                          |		     |(String)url                     |
												+-----------------------------------------+		     |(int)id                         |
												| Variables:                              |		     |(String)username                |
												+-----------------------------------------+		     |(String)email                   |
												| (API)apiConfig			  |		     |(String)first_name              |
												| (String)token                           |		     |(String)last_name               |
												| (User)user                              |		     |(String)date_joined             |
												| (int)timeline_id                        |		     |(String)last_login              |
												| (int)event_id                           |		     +--------------------------------+
												| (ArrayList<Timeline>)timelineArrayList  |		     |Functions:                      |
												| (ArrayList<Event>)eventArrayList        |		     +--------------------------------+
												+-----------------------------------------+		     |(String)getUser                 |
												| Functions:                              |		     |(void)setUser                   |
												+-----------------------------------------+		     |(int)getId                      |
												| (Logger)LOG				  |		     |(void)setId                     |
												| (User)getActiveUser                     |		     |(String)getUsername             |
												| (int)getTimeline_id                     |		     |(void)setUsername               |
												| (void)setTimeline_id                    |		     |(String)getEmail                |
												| (int)getEvent_id                        |		     |(void)setEmail                  |
												| (void)setEvent_id                       |		     |(String)getFirst_name           |
												| (Boolean)loginUser                      |		     |(void)setFirst_name             |
												| (void)getUser                           |		     |(String)getLast_name            |
												| (void)updateUser                        |		     |(void)setLast_name              |
												| (void)getTimelines                      |		     |(LocalDateTime)getDate_joined   |
												| (void)createTimeline                    |		     |(void)setDate_joined            |
												| (void)updateTimeline                    |		     |(String)getLast_login           |
												| (void)deleteTimeline                    |		     |(void)setLast_login             |
												| (void)getEvents                         |		     +--------------------------------+
												| (void)createEvent                       |
												+-----------------------------------------+
												| (void)deleteEvent                       |
												| (void)updateAPIconfig                   |
												| (Timeline)getActiveTimeline             |
												| (Event)getActiveEvent                   |
												+-----------------------------------------+
